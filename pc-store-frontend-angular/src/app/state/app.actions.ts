import {createAction, props} from "@ngrx/store";
import {InternalOrdersState} from "./app.reducer";

export const loadInternalOrders = createAction(
  '[InternalOrder] Load InternalOrders'
);

// TODO add interface
export const loadInternalOrdersSuccess = createAction(
  '[InternalOrder] Load InternalOrders Success',
  props<{ payload: InternalOrdersState }>()
);

// TODO add interface
export const loadInternalOrdersFailure = createAction(
  '[InternalOrder] Load InternalOrders Failure'
);
