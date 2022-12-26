import type {PayloadAction} from '@reduxjs/toolkit'
import {createSlice} from '@reduxjs/toolkit'

export interface UserState {
    isAuthenticated: boolean;
    fullname?: string;
    token?: string;
}

const getAnonymous = (): UserState => {
    return {isAuthenticated: false};
}

const initialState: UserState = getAnonymous()

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        setAuthentication: (state, action: PayloadAction<UserState>) => {
            return {
                ...state,
                isAuthenticated: action.payload.isAuthenticated,
                fullname: action.payload.fullname,
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
