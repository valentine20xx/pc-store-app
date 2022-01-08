import {createReducer, on} from '@ngrx/store';
import {InternalOrderShortDTO} from '../model/model';
import {loadInternalOrders, loadInternalOrdersFailure, loadInternalOrdersSuccess} from "./app.actions";

export interface InternalOrdersState {
  internalOrders: InternalOrderShortDTO[];
  loading?: boolean;
  hasError?: boolean;
}

export const appStateReducer = createReducer<InternalOrdersState>(
  {
    internalOrders: [],
    hasError: false,
    loading: false,
  },
  on(loadInternalOrders, (state) => {
    return {
      ...state,
      internalOrders: [], loading: true, hasError: false
    }
  }),
  on(loadInternalOrdersSuccess, (state, action) => {
    return {
      ...state,
      internalOrders: action.payload.internalOrders, hasError: action.payload.hasError, loading: action.payload.loading
    }
  }),
  on(loadInternalOrdersFailure, (state) => {
    return {
      ...state,
      internalOrders: [], loading: false, hasError: true
    }
  })
);
