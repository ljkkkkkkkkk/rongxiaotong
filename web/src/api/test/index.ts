import http from '@/api'
import { Test } from '@/interface';

export const getTest = () => {
	return http.get<Test>(`/test/tmp`);
};