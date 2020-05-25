package com.example.myapplication.view;

import com.example.myapplication.globalservicecases.GlobalServiceCases;

public interface BaseView {
    /**
     * invoked to display progress.
     *
     * @param globalServiceCases recommended when multiple request have different outcomes.
     */
    void showProgress(GlobalServiceCases globalServiceCases);

    /**
     * invoked to dismiss progress.
     *
     * @param globalServiceCases recommended when multiple request have different outcomes.
     */
    void dismissProgress(GlobalServiceCases globalServiceCases);

    /**
     * invoked to notify server/network issues.
     *
     * @param globalServiceCases recommended when multiple request have different outcomes.
     * @param message   beneficial to pass the server message and status if necessary.
     */
    void onServerNetworkIssue(GlobalServiceCases globalServiceCases,
                              String message);



}
