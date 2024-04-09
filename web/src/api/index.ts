import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse,AxiosError } from "axios";
import store from "@/store";


const config = {
	baseURL: import.meta.env.VITE_API_URL as string,
	timeout: 10000,
	withCredentials: true
};

class RequestHttp {
	service: AxiosInstance;
	public constructor(config: AxiosRequestConfig) {
		this.service = axios.create(config);
		
		this.service.interceptors.request.use(
			(config: any) => {
				const token = store.getState().login.token
				if(token && token != '')
				config.headers['Authorization'] = token; 

				return config;
			},
			(error: AxiosError) => {
				return Promise.reject(error);
			}
		);


		this.service.interceptors.response.use(
			(response: AxiosResponse) => {
				const { data, status, statusText } = response;
                if (status != 200) {
                    return Promise.reject(statusText)
                }
				if (data.code != 20000) {
					return Promise.reject(data.message);
				}
				return data.data;
			},
		);
	}

	get<T>(url: string, params?: object, _object = {}): Promise<T> {
		return this.service.get(url, { params, ..._object });
	}
	
	post<T>(url: string, params?: object, _object = {}): Promise<T> {
			return this.service.post(url, params, _object);
	}

	delete<T>(url: string, params?: any, _object = {}): Promise<T> {
		return this.service.delete(url, { params, ..._object });
	}

	put<T>(url: string, params?: object, _object = {}): Promise<T> {
		return this.service.put(url, params, _object);
	}
}

export default new RequestHttp(config);