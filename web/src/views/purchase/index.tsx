import List from '@mui/material/List';
import Pagination from '@mui/material/Pagination';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import {useLayoutEffect, useState} from 'react'
import { Good, PageInfo } from '@/interface';
import styles from './index.module.scss'
const { Search } = Input;
import { Input, message} from 'antd'
import { useNavigate } from 'react-router-dom';
import { getNeedsByKey, getNeedsByPage } from '@/api/order';
import { useViewport } from '@/content/viewportContent';

const Purchase = () => {
  const {width} = useViewport()
  const navigateTo = useNavigate();
const [data, setData] = useState({total: 0, list: []} as PageInfo<Good>)

 const changePage = (e: any, v: any) => {
     const getData = async () => {
      try {
        const d = await getNeedsByPage(v)
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
        const d = await getNeedsByPage(1)
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }, [])

  const search = (key: string, page: number) => {
    const getData = async () => {
      try {
          if (!key) {
            const d = await getNeedsByPage(page)
            setData(d)
          } else {
            const d = await getNeedsByKey(key, page)
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
            <div className='content'>
              <List>
                {
                  data.list.map((x: Good, index: number) => {
                    var c = x.content
                    if (c.length > 15) {
                      c = c.substring(0, 15) + '...'
                    }
                    return (
                      <ListItem key={index} >
                        <ListItemButton onClick={() => navigateTo('/purchaseDetail/' + x.orderId)}>
                          <ListItemText>{c}</ListItemText>
                          <ListItemText className='text-title'>{x.title}</ListItemText>
                          <ListItemText className='time'>{x.updateTime}</ListItemText>
                        </ListItemButton>
                      </ListItem>
                    )
                  })
                }
              </List>
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
              <div className='content'>
                <List>
                  {
                    data.list.map((x: Good, index: number) => {
                      var c = x.content
                      if (c.length > 15) {
                        c = c.substring(0, 15) + '...'
                      }
                      return (
                        <ListItem key={index} >
                          <ListItemButton onClick={() => navigateTo('/purchaseDetail/' + x.orderId)}>
                            <div className='cc'>
                              <span className='c'>{c}</span>
                              <span className='t'>{x.title}</span>
                              <span className='u'>{x.updateTime}</span>
                            </div>
                          </ListItemButton>
                        </ListItem>
                      )
                    })
                  }
                </List>
              </div>
          </div>
      }
    </>

  )
}

export default Purchase