package com.ebest.frame.annomationapilib.route.route;

/**
 * <p>
 *  Base on the {@link IBaseRoute}
 * </p>
 */
public interface IActionRoute extends IBaseRoute<IActionRoute>{

    class EmptyActionRoute extends EmptyBaseRoute<IActionRoute> implements IActionRoute {

        public EmptyActionRoute(InternalCallback internal) {
            super(internal);
        }
    }
}
