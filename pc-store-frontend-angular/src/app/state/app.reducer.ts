import {createReducer, on} from '@ngrx/store';
import {InternalOrderShortDTO} from '../model/model';
import {addInternalOrder, loadInternalOrders, loadInternalOrdersFailure, loadInternalOrdersSuccess, login, loginFailure, loginSuccess, logout, logoutSuccess} from "./app.actions";

export interface ServiceInterconnection {
  loading: boolean;
  hasError: boolean;
}

export interface InternalOrdersState extends ServiceInterconnection {
  internalOrders: InternalOrderShortDTO[];

}

export interface LoginState extends ServiceInterconnection {
  logged: boolean;
  name?: string;
}

export const loginStateReducer = createReducer<LoginState>(
  {
    hasError: false,
    loading: false,
    logged: false,
  },
  on(login, (state) => {
    return {...state, loading: true, hasError: false};
  }),
  on(loginSuccess, (state, action) => {
    return {
      ...state,
      ...action.payload
    };
  }),

  on(loginFailure, (state) => {
    return {
      ...state,
      loading: false, hasError: true
    };
  }),
  on(logout, (state) => {
    return {...state, loading: true, hasError: false};
  }),
  on(logoutSuccess, (state, action) => {
    return {
      ...state,
      ...action.payload
    };
  }),
);

export const internalOrdersStateReducer = createReducer<InternalOrdersState>(
  {
    internalOrders: [],
    hasError: false,
    loading: false,
  },
  on(loadInternalOrders, (state) => {
    return {
      ...state,
      internalOrders: [], loading: true, hasError: false
    };
  }),
  on(loadInternalOrdersSuccess, (state, action) => {
    return {
      ...state,
      ...action.payload
    };
  }),
  on(loadInternalOrdersFailure, (state) => {
    return {
      ...state,
      internalOrders: [], loading: false, hasError: true
    };
  }),
  on(addInternalOrder, (state) => {
    return {
      ...state
    };
  })
);
