# Project Taba support Contributing rules

## Coding convention

* Dùng default convention của intellij 
* Đối với file **.java** indent = tab

  * Vào **Setting > Editor > Code Style > java**    
  * check **User tab character**
  * check **Smart tab**    
  * Tab size: 4
  * Indent : 4 
  * Continuation indent: 8
  
## Structure

* Default package **vn.azteam.tabasupport**
* Các api, service, controller dùng chung đặt trong **vn.azteam.tabasupport.core** tương ứng
  * **vn.azteam.tabasupport.core.controller** MVC controller cho web
  * **vn.azteam.tabasupport.core.rest** API
  * **vn.azteam.tabasupport.core.object** các Dao và model 
  * **vn.azteam.tabasupport.core.service** các service
* Các module thì đặt tên package theo format  **vn.azteam.tabasupport.modules.{module name}** 
  * Cấu trúc module giống như của **vn.azteam.tabasupport.core**
  * Các util chỉ dùng cho module thì tạo package theo format **vn.azteam.tabasupport.modules.{module name}.util**
* **vn.azteam.tabasupport.util** Chứa các util dùng chung.
* **Notice** kiểm tra các util trước khi thêm để tránh trùng code. (Document sẽ viết trên wiki gitlab hoặc sẽ generate ra javadoc - link sẽ có sau)

## Coding comment

sử dụng default của java xem **vn.azteam.tabasupport.core.service.AccountService**

## Commit rules

* Tạo branch mới từ **develop** tên branch đặt theo **function or module**
* Trước khi commit source nhớ **Rebase**
* Commit theo format **Refs #{ticketId} - {nội dung commit}**
* **Notice** Các commit mà function không có comment hoặc unit test thì sẽ không merge.