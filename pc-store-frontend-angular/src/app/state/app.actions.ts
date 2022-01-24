import {createAction, props} from "@ngrx/store";
import {InternalOrdersState, LoginState} from "./app.reducer";
import {NewInternalOrderMPDTO} from "../model/model";

const internalOrdersModule = '[Internal orders]';
const loginModule = '[Login]';

export const loadInternalOrders = createAction(
  `${internalOrdersModule} Load internal orders`
);

// TODO add interface
export const loadInternalOrdersSuccess = createAction(
  `${internalOrdersModule} Load internal orders success`,
  props<{ payload: InternalOrdersState }>()
);

// TODO add interface
export const loadInternalOrdersFailure = createAction(
  `${internalOrdersModule} Load internal orders failure`
);

export const login = createAction(
  `${loginModule} Load`
);

export const loginSuccess = createAction(
  `${loginModule} Load success`,
  props<{ payload: LoginState }>()
);

export const loginFailure = createAction(
  `${loginModule} Load failure`
);

export const logout = createAction(
  `${loginModule} Logout`
);

export const logoutSuccess = createAction(
  `${loginModule} Logout success`,
  props<{ payload: LoginState }>()
);

export const addInternalOrder = createAction(
  `${internalOrdersModule} Add internal order`,
  props<{ payload: NewInternalOrderMPDTO }>()
);

export const addInternalOrderSuccess = createAction(
  `${internalOrdersModule} Add internal order success`
  // ,  props<{ payload: InternalOrdersState }>()
);
