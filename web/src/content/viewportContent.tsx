import {createContext, useState, useEffect, useContext} from "react";
const viewportContext = createContext({} as viewport);

interface viewport {
    width: number,
    height: number
}

const ViewportProvider = ({ children }: {children: any}) => {
  const [width, setWidth] = useState(window.innerWidth);
  const [height, setHeight] = useState(window.innerHeight);

  const handleWindowResize = () => {
    setWidth(window.innerWidth);
    setHeight(window.innerHeight);
  }

  useEffect(() => {
    window.addEventListener("resize", handleWindowResize);
    return () => window.removeEventListener("resize", handleWindowResize);
  }, []);

  return (
    <viewportContext.Provider value={{ width, height }}>
      {children}
    </viewportContext.Provider>
  );
};

export default ViewportProvider

export const useViewport = () => {
  const { width, height } = useContext(viewportContext);
  return { width, height };
}