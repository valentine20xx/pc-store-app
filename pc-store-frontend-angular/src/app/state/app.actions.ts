import {createAction, props} from '@ngrx/store';
import {Observable} from 'rxjs';
import {InternalOrderDTO, InternalOrderShortDTO, NewInternalOrderMPDTO} from '../model/model';

const internalOrdersModule = '[Internal orders]';
const internalOrderModule = '[Internal order]';
const loginModule = '[Login]';

export const loadInternalOrders = createAction(
  `${internalOrdersModule} Load internal orders`
);

export const loadInternalOrdersSuccess = createAction(
  `${internalOrdersModule} Load internal orders success`,
  props<{
    internalOrders: InternalOrderShortDTO[],
    internalOrders$?: Observable<InternalOrderShortDTO[]>
  }>()
);

export const loadInternalOrdersFailure = createAction(
  `${internalOrdersModule} Load internal orders failure`,
  props<{ message: string }>()
);

export const loadInternalOrdersClosed = createAction(
  `${internalOrdersModule} Load internal orders closed`,
  // props<ILoadInternalOrdersSuccess>()
);

// LOGIN

export const login = createAction(
  `${loginModule} Login`
);

export const loginSuccess = createAction(
  `${loginModule} Login success`,
  props<{ name: string, logged: boolean }>()
);

export const loginFailure = createAction(
  `${loginModule} Login failure`
);

export const logout = createAction(
  `${loginModule} Logout`
);

export const logoutSuccess = createAction(
  `${loginModule} Logout success`,
  props<{ name: string, logged: boolean }>()
);

export const logoutFailure = createAction(
  `${loginModule} Logout failure`
);

export const addInternalOrder = createAction(
  `${internalOrdersModule} Add internal order`,
  props<{
    newInternalOrderMPDTO: NewInternalOrderMPDTO,
    formData: FormData
  }>()
);

export const addInternalOrderSuccess = createAction(
  `${internalOrdersModule} Add internal order success`
  // ,  props<{ payload: InternalOrdersState }>()
);

export const loadInternalOrder = createAction(
  `${internalOrderModule} Load internal order`,
  props<ILoadInternalOrder>()
);

export interface ILoadInternalOrder {
  id: string
}

export const loadInternalOrderSuccess = createAction(
  `${internalOrderModule} Load internal order success`,
  props<{ internalOrderDTO: InternalOrderDTO }>()
);

export const loadInternalOrderFailure = createAction(
  `${internalOrderModule} Load internal order failure`
);

export const loadInternalOrderClosed = createAction(
  `${internalOrderModule} Load internal order closed`
);
