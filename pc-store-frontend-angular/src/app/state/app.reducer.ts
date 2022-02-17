import {createReducer, on} from '@ngrx/store';
import {Observable} from 'rxjs';
import {InternalOrderDTO, InternalOrderShortDTO, NewInternalOrderMPDTO} from '../model/model';
import {
  addInternalOrder,
  addInternalOrderSuccess,
  loadInternalOrder,
  loadInternalOrderClosed,
  loadInternalOrderFailure,
  loadInternalOrders,
  loadInternalOrdersClosed,
  loadInternalOrdersFailure,
  loadInternalOrdersSuccess,
  loadInternalOrderSuccess,
  login,
  loginFailure,
  loginSuccess,
  logout,
  logoutSuccess
} from './app.actions';

export interface ServiceInterconnection {
  status: 'INITIALIZED' | 'SEND' | 'RECEIVED' | 'FAILURE' | 'CLOSED'
}

export interface InternalOrdersState extends ServiceInterconnection {
  internalOrders: InternalOrderShortDTO[];
  internalOrders$?: Observable<InternalOrderShortDTO[]>
}

export interface InternalOrderState extends ServiceInterconnection {
  internalOrder?: InternalOrderDTO;
}

export interface AddInternalOrderState extends ServiceInterconnection {
  newInternalOrderMPDTO?: NewInternalOrderMPDTO,
  formData?: FormData
}

export interface LoginState extends ServiceInterconnection {
  logged: boolean;
  name?: string;
}

// TODO: add states
export const loginStateReducer = createReducer<LoginState>(
  {
    logged: false,
    status: 'INITIALIZED'
  },
  on(login, (state): LoginState => {
    return {
      ...state,
      status: 'SEND'
    };
  }),
  on(loginSuccess, (state, action): LoginState => {
    return {
      ...state,
      logged: action.logged,
      name: action.name,
      status: 'RECEIVED',
    };
  }),
  on(loginFailure, (state, action): LoginState => {
    return {
      ...state,
      status: 'FAILURE'
    };
  }),
  on(logout, (state): LoginState => {
    return {...state, status: 'SEND'};
  }),
  on(logoutSuccess, (state, action): LoginState => {
    return {
      ...state,
      name: action.name,
      logged: action.logged,
      status: 'RECEIVED'
    };
  }),
);

export const internalOrdersStateReducer = createReducer<InternalOrdersState>(
  {
    internalOrders: [],
    status: 'INITIALIZED'
  },
  on(loadInternalOrders, (state): InternalOrdersState => {
    return {
      ...state,
      status: 'SEND',
      internalOrders: []
    };
  }),
  on(loadInternalOrdersSuccess, (state, action): InternalOrdersState => {
    console.log('RECEIVED:', state, action);

    return {
      ...state,
      internalOrders: action.internalOrders,
      internalOrders$: action.internalOrders$,
      status: 'RECEIVED'
    };
  }),
  on(loadInternalOrdersFailure, (state): InternalOrdersState => {
    return {
      ...state,
      internalOrders: [],
      status: 'FAILURE'
    };
  }),
  on(loadInternalOrdersClosed, (state): InternalOrdersState => {
    console.log('CLOSED:', state);

    return {
      ...state,
      // internalOrders: [],
      status: 'CLOSED'
    }
  }),
  on(addInternalOrder, (state, action) => {
    console.info('addInternalOrder called:', action.newInternalOrderMPDTO, action.formData);

    return {
      ...state
    };
  })
);

export const addInternalOrderStateReducer = createReducer<AddInternalOrderState>({
    newInternalOrderMPDTO: undefined,
    formData: undefined,
    status: 'INITIALIZED'
  },
  on(addInternalOrder, (state, action) => {
    return {
      ...state,
      newInternalOrderMPDTO: action.newInternalOrderMPDTO,
      formData: action.formData,
      status: 'SEND'
    }
  }),
  on(addInternalOrderSuccess, (state) => {
    return {
      ...state,
      newInternalOrderMPDTO: undefined,
      formData: undefined,
      status: 'RECEIVED'
    }
  })
);

export const internalOrderStateReducer = createReducer<InternalOrderState>({
    internalOrder: undefined,
    status: 'INITIALIZED'
  },
  on(loadInternalOrder, (state): InternalOrderState => {
    return {
      ...state,
      internalOrder: undefined,
      status: 'SEND'
    }
  }),
  on(loadInternalOrderSuccess, (state, action): InternalOrderState => {
    return {
      ...state,
      internalOrder: action.internalOrderDTO,
      status: 'RECEIVED'
    }
  }),
  on(loadInternalOrderFailure, (state, action): InternalOrderState => {
    return {
      ...state,
      internalOrder: undefined,
      status: 'FAILURE'
    }
  }),
  on(loadInternalOrderClosed, (state): InternalOrderState => {
    return {
      ...state,
      internalOrder: undefined,
      status: 'CLOSED'
    }
  })
);
