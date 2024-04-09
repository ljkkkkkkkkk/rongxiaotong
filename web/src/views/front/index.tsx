import {useLayoutEffect, useState} from 'react'
import Pagination from '@mui/material/Pagination';
import styles from './index.module.scss'
import { Good, PageInfo } from '@/interface';
import { useNavigate } from 'react-router-dom';
import { Input, message} from 'antd'
import { getAllByPage, getByKey } from '@/api/order';
import { getImg} from '@/util';
import { useViewport } from '@/content/viewportContent';
const { Search } = Input;
const Front = () => {

  const {width} = useViewport();
  const navigateTo = useNavigate();
  const [data, setData] = useState({
    total: 0,
    list: []
  } as PageInfo<Good>)

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
        message.error("获取数据失败: "+err)
      }
    }
    getData()
  }, [])

  const search = (key: string, page: number) => {
    const getData = async () => {
      try {
          if (!key) {
            const d = await getAllByPage(page)
            setData(d)
          } else {
            const d = await getByKey(key, page)
            setData(d)
          }
      } catch (err) {
        message.error("获取数据失败: "+err)
      }
    }
    getData()
  }

  return (
    <>
      {
        width>500?
          <div className={styles.root}>
            <div className='head'>
              <Search enterButton onSearch={(e) => search(e, 1)}/>
            </div>
            <div className='list'>
              {
                data.list.map((x: Good, index: number) => {
                  return (
                    <div className='item' key={index}>
                      <img src={getImg(index)}/>
                      <div>
                        <span className='title' onClick={() => navigateTo('/goodDetail/' + x.orderId)}>{x.type==='needs'?'[需]':'[供]'} {x.title}</span>
                        <span className='price'>{x.price}</span>
                        <span className='date'>{x.ownName} | {x.updateTime}</span>
                      </div>
                    </div>
                  )
                })
              }
            </div>
            <div className='footer'>
              <Pagination shape='rounded' count={Math.ceil(data.total/30)} onChange={changePage}/>
            </div>
          </div>
        :
          <div className={styles.mobile}>
              <div className='head'>
                <Search enterButton onSearch={(e) => search(e, 1)}/>
              </div>
              <div className='list'>
                {
                  data.list.map((x: Good, index: number) => {
                    return (
                      <div className='item' key={index}>
                        <img src={getImg(index)}/>
                        <div>
                          <span className='title' onClick={() => navigateTo('/goodDetail/' + x.orderId)}>{x.type==='needs'?'[需]':'[供]'} {x.title}</span>
                          <span className='price'>{x.price}</span>
                          <span className='date'>{x.ownName} | {x.updateTime}</span>
                        </div>
                      </div>
                    )
                  })
                }
              </div>
          </div>
      }
    </>

  )
}

export default Front