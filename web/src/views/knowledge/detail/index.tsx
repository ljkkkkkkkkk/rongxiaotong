

import { useParams } from 'react-router-dom';
import styles from './index.module.scss'
import { Discuss, Knowledge, PageInfo } from '@/interface';
import { getAllByKnowledge } from '@/api/discuss'
import {useLayoutEffect, useState} from 'react'
import { getAllById } from '@/api/konwledege';
import { TextareaAutosize } from '@mui/base/TextareaAutosize';


const KnowDetail = () => {
  const {index} = useParams(); // 获取路由参数中的index
  const [data, setData] = useState([] as Discuss[])
  const [data2,setData2] = useState({} as Knowledge)
   const [commentInput, setCommentInput] = useState(''); // 初始化评论框的值为空字符串
  useLayoutEffect(() => {
    if (index == undefined ||  index== null) {
      alert("id错误")
      return
    }
    const getData = async () => {
      try {
        const d = await getAllByKnowledge(parseInt(index))
        const k = await getAllById(parseInt(index))
        setData2(k)
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  })
  const handleSubmitComment = () => {
    setCommentInput('');
  };


      return (
      
        <div className={styles.root}>
          <div className="content">
            <div className="title">{data2.title}</div>
            <div className="picture">
              <img src="123456" alt="Content" />
            </div>
            <div className="description">{data2.content}</div>
            <div>
            <div>
            {data.map((item) => (
              <div key={item.discussId} className="container">
              <div className={styles.item}>
                <strong>用户名:</strong> {item.ownName}
              </div>
              <div className={styles.item}>
                <strong>评论:</strong> {item.content}
              </div>
              <div className={styles.item}>
                <strong>日期:</strong> {item.createTime}
              </div>
              </div>
            ))}
          </div>
            <div>
       </div>

       <div> 
        评论框<TextareaAutosize 
         placeholder="在这里输入评论"/>;
      </div>
      <button onClick={() => handleSubmitComment()}>提交评论</button>
            </div>
               
            </div>
          </div>
        );
  
}
export default KnowDetail