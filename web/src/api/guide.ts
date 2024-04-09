import http from '@/api'
import { Expert, PageInfo, Question } from '@/interface'

export const findAllQues = (page: number) => {
    return http.get<PageInfo<Question>>('question/findAllQues/'+page)
}

export const selectById = (id: number) => {
    return http.get<Question>('question/selectId/'+id)
}

export const findExpert = (page: number) => {
    return http.get<PageInfo<Expert>>('question/findExpert/'+page)
}

export const findByKey = (key: string, page: number) => {
    return http.get<PageInfo<Question>>('question/findPageQues/'+key+'/'+page)
}