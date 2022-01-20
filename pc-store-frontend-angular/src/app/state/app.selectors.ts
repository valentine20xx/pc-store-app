import {AppState} from "../app.module";

export const selectFeature = (state: AppState) => state.internalOrdersState;
