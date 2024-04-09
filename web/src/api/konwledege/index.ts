import http from '@/api'
import { Knowledge, PageInfo} from '@/interface'

export const getAllByPage = (page: number) => {
    return http.get<PageInfo<Knowledge>>('/knowledge/'+page)
}
export const getAllById = (index: number) => {
    return http.get<Knowledge>('/knowledge/selectById/'+index)
}