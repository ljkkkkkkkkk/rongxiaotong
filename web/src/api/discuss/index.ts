import http from '@/api'
import { Discuss, PageInfo } from '@/interface'

export const getAllByKnowledge = (knowledge: number) => {
    return http.get<Discuss[]>('knowledge/selectByKnowledge/'+knowledge)
}