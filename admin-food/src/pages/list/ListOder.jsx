import "./list.scss"
import Sidebar from "../../components/sidebar/Sidebar"
import Navbar from "../../components/navbar/Navbar"
import DatatableOder from "../../components/datatable/DatatableOder"

const ListOder = () => {
  return (
    <div className="list">
      <Sidebar/>
      <div className="listContainer">
        <Navbar/>
        <DatatableOder/>
      </div>
    </div>
  )
}

export default ListOder