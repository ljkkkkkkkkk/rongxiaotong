import {useState, useLayoutEffect} from 'react'
import Pagination from '@mui/material/Pagination';
import { useNavigate } from 'react-router-dom';
import {  Button} from '@mui/material';
import styles from './index.module.scss'
import { Expert, PageInfo, Question } from '@/interface';
import { findAllQues, findByKey, findExpert } from '@/api/guide';
import { getExpert } from '@/util';
import { Input, message} from 'antd'
import { useViewport } from '@/content/viewportContent';

const { Search } = Input;
const Guide = () => {
  const navigateTo = useNavigate();
  const {width} = useViewport()

    const [data, setData] = useState({
    total: 0,
    list: []
  } as PageInfo<Question>)
  const [dataExpert, setDE] = useState({
    total: 0,
    list: []
  } as PageInfo<Expert>)

  const changePage = (e: any, v: any) => {
     const getData = async () => {
      try {
        const d = await findAllQues(v)
        setData(d)
      } catch (err) {
        message.error("获取数据失败: "+err)
      }
    }
    getData()   
  }
  const search = (key: string, page: number) => {
    const getData = async () => {
      try {
          if (!key) {
            const d = await findAllQues(page)
            setData(d)
          } else {
            const d = await findByKey(key, page)
            setData(d)
          }
      } catch (err) {
        message.error("获取数据失败: "+err)
      }
    }
    getData()
  }
  useLayoutEffect(() => {
    const getData = async () => {
      try {
        const d = await findAllQues(1)
        const de = await findExpert(1)
        setData(d)
        setDE(de)
      } catch (err) {
        message.error("获取数据失败: "+err)
      }
    }
    getData()
  }, [])
  return (
    <>
      {
        width>500?
        <div className={styles.root}>
          <div className='left'>
            <div className='head'>
              <Search enterButton onSearch={(e) => search(e, 1)}/>
            </div>
            <div className='content'>
              {
                data.list.map((x: Question, index: number) => {
                  return (
                    <div className='item' key={index}>
                        <span className='title' onClick={() => navigateTo('/guideDetail/' + x.id)}>{x.title}</span>
                        <span className='asker'>提问者：{x.questioner} 专家：{x.expertName}</span>
                    </div>
                  )
                })
              }
            </div>
            <div className='footer'>
              <Pagination shape='rounded' count={Math.ceil(data.total/30)} onChange={changePage}/>
            </div>
          </div>
          <div className='right'>
            <div className='head'>
              <div className='body'>
                <Button variant="text" size='large'>在线问答</Button>
                |
                <Button variant="text" size='large'>专家预约</Button>
              </div>
            </div>
            <div className='content'>
              <div className='chead'>
                <span>专家列表</span>
              </div>
              <div className='body'>
                {
                  dataExpert.list.map((x: Expert, index: number) => {
                    return (
                      <div className='item' key={index}>
                        {
                          width>600?<img src={getExpert(index)}/>:<></>
                        }
                        <div className='info'>
                          <span>专家姓名：{x.realName}</span>
                          <span>职称：{x.position}</span>
                          <span>从事专业：{x.profession}</span>
                          <span>电话：{x.phone}</span>
                          <span>单位：{x.belong}</span>
                        </div>
                      </div>
                    )
                  })
                }
              </div>
            </div>
          </div>
        </div>
      :
        <div className={styles.mobile}>
            <div className='head'>
              <Search enterButton onSearch={(e) => search(e, 1)}/>
            </div>
            <div className='content'>
              {
                data.list.map((x: Question, index: number) => {
                  return (
                    <div className='item' key={index}>
                        <span className='title' onClick={() => navigateTo('/guideDetail/' + x.id)}>{x.title}</span>
                        <span className='asker'>提问者：{x.questioner} 专家：{x.expertName}</span>
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

export default Guide