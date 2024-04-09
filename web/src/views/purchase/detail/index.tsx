import {useLayoutEffect, useState} from 'react'
import styles from './index.module.scss'
import { useParams } from 'react-router-dom'
import { Good } from '@/interface'
import { getById } from '@/api/order'
import { getImg} from '@/util'
import { useViewport } from '@/content/viewportContent'

const PurchaseDetail = () => {
  const {width} = useViewport();
  const {id} = useParams()
  const [data, setData] = useState({} as Good)
  useLayoutEffect(() => {
    if (id == undefined || id == null) {
      alert("id错误")
      return
    }
    const getData = async () => {
      try {
        const d = await getById(parseInt(id))
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }, [])
  return (
    <>
    {
      width>500?
        <div className={styles.root}>
            <div className='title'>{data.title}</div>
            <div className='time'>
                <span>{data.createTime}</span>
                <span>{data.updateTime}</span>
            </div>
            <img src={getImg(parseInt(id as string))}/>
            <p>{data.content}</p>
        </div>
      :
        <div className={styles.mobile}>
            <div className='title'>{data.title}</div>
            <div className='time'>
                <span>{data.createTime}</span>
                <span>{data.updateTime}</span>
            </div>
            <img src={getImg(parseInt(id as string))}/>
            <p>{data.content}</p>
        </div>
    }
    </>

  )
}

export default PurchaseDetail