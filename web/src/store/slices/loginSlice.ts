import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    token: ""
}

const loginSlice = createSlice({
    name: 'login',
    initialState: initialState,
    reducers: {
        setToken(state, {payload}) {
            state.token = payload
        },
        clearToken(state) {
            state.token = ''
        }
    } 
})

export const {setToken, clearToken} = loginSlice.actions;
export default loginSlice.reducer;