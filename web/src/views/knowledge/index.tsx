import React from 'react'
import styles from './index.module.scss'
import { useNavigate } from 'react-router-dom';
import {useLayoutEffect, useState,useEffect} from 'react'
import { Knowledge, PageInfo } from '@/interface';
import axios from 'axios'; // 请确保已安装 Axios
import { getAllByPage } from '@/api/konwledege';
import Pagination from '@mui/material/Pagination';


const KnowledgePage = () => {

  const navigate = useNavigate();
  const maxContentLength=50;
  const [data, setData] = useState({
    total: 0,
    list: []
  } as PageInfo<Knowledge>)

  const changePage = (e: any, v: any) => {
     const getData = async () => {
      try {
        const d = await getAllByPage(v)
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()   
  }

  useLayoutEffect(() => {
    const getData = async () => {
      try {
        const d = await getAllByPage(1)
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }, [])

  return (
    <div className={styles.root}>
      <div className="knowledge-list">
        {data.list.map((Knowledge, index) => (
          <div key={index} className="knowledge-item">
            <h2 onClick={() => navigate('/knowDetail/' + Knowledge.knowledgeId)}>{Knowledge.title}</h2>
            <p>
              {/* 如果内容长度超过最大长度，则截断并添加 "..." */}
              {Knowledge.content.length > maxContentLength
                ? Knowledge.content.slice(0, maxContentLength) + '...'
                : Knowledge.content}
            </p>
          </div>
        ))}
      </div>
      <div className='footer'>
          <Pagination shape='rounded' count={Math.ceil(data.total / 30)} onChange={changePage} />
      </div>
    </div>
  );
}

export default KnowledgePage;