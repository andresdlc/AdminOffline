
package com.bolivariano.frameworkseguridadtypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LoginAplicacion_out" type="{http://www.bolivariano.com/FrameworkSeguridadTypes}LoginAplicacionType_out2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "loginAplicacionOut"
})
@XmlRootElement(name = "LoginAplicacion_out")
public class LoginAplicacionOut {

    @XmlElement(name = "LoginAplicacion_out", required = true)
    protected LoginAplicacionTypeOut2 loginAplicacionOut;

    /**
     * Obtiene el valor de la propiedad loginAplicacionOut.
     * 
     * @return
     *     possible object is
     *     {@link LoginAplicacionTypeOut2 }
     *     
     */
    public LoginAplicacionTypeOut2 getLoginAplicacionOut() {
        return loginAplicacionOut;
    }

    /**
     * Define el valor de la propiedad loginAplicacionOut.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginAplicacionTypeOut2 }
     *     
     */
    public void setLoginAplicacionOut(LoginAplicacionTypeOut2 value) {
        this.loginAplicacionOut = value;
    }

}
