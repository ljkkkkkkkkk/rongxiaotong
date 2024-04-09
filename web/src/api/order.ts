import http from '@/api'
import { Good, PageInfo } from '@/interface'

export const getAllByPage = (page: number) => {
    return http.get<PageInfo<Good>>('/order/All/'+page)
}

export const getById = (id: number) => {
    return http.get<Good>('/order/selectById/'+id)
}

export const getNeedsByPage = (page: number) => {
    return http.get<PageInfo<Good>>('/order/needs/'+page)
}

export const getByKey = (key:string, page: number) => {
    return http.get<PageInfo<Good>>('order/searchAllByKeys/'+key+'/'+page)
}

export const getNeedsByKey = (key:string, page: number) => {
    return http.get<PageInfo<Good>>('order/searchNeedsByKeys/'+key+'/'+page)
}