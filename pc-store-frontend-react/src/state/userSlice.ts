import type {PayloadAction} from '@reduxjs/toolkit'
import {createSlice} from '@reduxjs/toolkit'

export enum ROLES {
    READ = "ROLE_READ",
    EDIT = "ROLE_EDIT"
}

export interface UserState {
    isAuthenticated: boolean;
    fullname?: string;
    roles: Array<ROLES>;
    token?: string;
}

const getAnonymous = (): UserState => {
    return {isAuthenticated: false, roles: []};
}

const initialState: UserState = getAnonymous()

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setAuthentication: (state, action: PayloadAction<UserState>) => {
            console.debug("action", action);

            return {
                ...state,
                isAuthenticated: action.payload.isAuthenticated,
                fullname: action.payload.fullname,
                roles: action.payload.roles,
                token: action.payload.token
            }
        },
        clearAuthentication: (state) => {
            return {
                ...state,
                ...initialState
            }
        }
    },
})

// Action creators are generated for each case reducer function
export const {setAuthentication, clearAuthentication} = userSlice.actions

export default userSlice.reducer
