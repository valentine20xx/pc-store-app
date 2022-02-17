import {AppState} from '../app.module';

export const internalOrdersStateFeature = (state: AppState) => state.internalOrdersState;
export const internalOrdersStateFeatureInternalOrders = (state: AppState) => state.internalOrdersState.internalOrders$;
export const loginStateFeature = (state: AppState) => state.loginState;
export const internalOrderStateFeature = (state: AppState) => state.internalOrderState
