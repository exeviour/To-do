/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.statistics;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import com.example.android.architecture.blueprints.todoapp.AbstractBasePresenter;
import com.example.android.architecture.blueprints.todoapp.UseCase;
import com.example.android.architecture.blueprints.todoapp.UseCaseHandler;
import com.example.android.architecture.blueprints.todoapp.statistics.domain.usecase.GetStatistics;
import com.example.android.architecture.blueprints.todoapp.statistics.domain.model.Statistics;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment}), retrieves the data and updates
 * the UI as required.
 */
public class StatisticsPresenter
        extends AbstractBasePresenter<StatisticsContract.View>
        implements StatisticsContract.Presenter {
    private final GetStatistics mGetStatistics;


    public StatisticsPresenter(
            @NonNull UseCaseHandler useCaseHandler,
            @NonNull StatisticsContract.View statisticsView,
            @NonNull GetStatistics getStatistics) {
        super(statisticsView, useCaseHandler);

        mGetStatistics = checkNotNull(getStatistics,"getStatistics cannot be null!");

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadStatistics();
    }

    private void loadStatistics() {
        mView.setProgressIndicator(true);

        schedule(mGetStatistics, new GetStatistics.RequestValues(),
                new UseCase.UseCaseCallback<GetStatistics.ResponseValue>() {
            @Override
            public void onSuccess(GetStatistics.ResponseValue response) {
                Statistics statistics = response.getStatistics();
                // The view may not be able to handle UI updates anymore
                if (!mView.isActive()) {
                    return;
                }
                mView.setProgressIndicator(false);

                mView.showStatistics(statistics.getActiveTasks(), statistics.getCompletedTasks());
            }

            @Override
            public void onError() {
                // The view may not be able to handle UI updates anymore
                if (!mView.isActive()) {
                    return;
                }
                mView.showLoadingStatisticsError();
            }
        });
    }
}
