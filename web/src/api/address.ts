import http from '@/api'
import { Address } from '@/interface'

export const getAddr = () => {
    return http.get<Address[]>('address/selectByOwnName')
}

export const addAddr = (x: Address) => {
    return http.post<null>('address/add', x)
}