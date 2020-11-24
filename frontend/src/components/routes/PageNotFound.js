import React from 'react'
import '../../styles/PaginaNoEncontrada.css'


const PageNotFound = () =>{
    return(
        <div className="row">
            <div className='col s12'>
                <i className="small material-icons left" id="exclamation-triangle">error_outline</i>
            </div>
            <div className='col s12'>
                <div class="numero">
                    404
                </div>
            </div>
            <div className='col s12'>
                <div class="leyenda">
                    Perdón! La pagina no existe
                </div>
            </div>
        </div>
            )

}


export default PageNotFound;