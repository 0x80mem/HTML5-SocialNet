
import axios from 'axios'
// const baseURL ="http://localhost:8088"
const baseURL= 'http://47.93.10.201/api'
const timeout = 10000
const axiosConfig = {
  baseURL,
  timeout,
};

const api = axios.create(axiosConfig);
const apiFm = axios.create({
  ...axiosConfig,
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
  },
});
export{api,apiFm}