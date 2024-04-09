import  { useState, useLayoutEffect } from 'react'
import styles from './index.module.scss'
import { useParams } from 'react-router-dom'
import { Question } from '@/interface'
import { selectById } from '@/api/guide'

const GuideDetail = () => {
  const {id} = useParams()
  const [data, setData] = useState({} as Question)
  useLayoutEffect(() => {
    if (id == undefined || id == null) {
      alert("id错误")
      return
    }
    const getData = async () => {
      try {
        const d = await selectById(parseInt(id))
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }, [])
  return (
    <div className={styles.root}>
        <div>
            <label>问题：</label>
            <p>{data.question}</p>
        </div>
        <div>
            <label>回答：</label>
            <p>{data.answer}</p>
        </div>
    </div>
  )
}

export default GuideDetail