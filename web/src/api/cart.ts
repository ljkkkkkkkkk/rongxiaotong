import http from '@/api'
import { Cart } from '@/interface'

export const add = (id: number) => {
    return http.post<null>('cart/add/'+id)
}

export const show = () => {
    return http.get<Cart[]>('cart/show')
}

export const deleteCart = (id: number) => {
    return http.delete<null>('cart/delete/'+id)
}

export const updataCount = (sid: number, c: number) => {
    return http.put<null>('cart/update/'+sid+'/'+c)
}

export const orderCommit = (aid: number, t: number, s: Cart[]) => {
    return http.post<null>('cart/commitOrder/'+aid+'/'+t,s)
}