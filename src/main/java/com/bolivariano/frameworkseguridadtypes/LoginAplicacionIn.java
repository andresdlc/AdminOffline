
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
 *         &lt;element name="LoginAplicacion_in" type="{http://www.bolivariano.com/FrameworkSeguridadTypes}LoginAplicacionType_in"/&gt;
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
    "loginAplicacionIn"
})
@XmlRootElement(name = "LoginAplicacion_in")
public class LoginAplicacionIn {

    @XmlElement(name = "LoginAplicacion_in", required = true)
    protected LoginAplicacionTypeIn loginAplicacionIn;

    /**
     * Obtiene el valor de la propiedad loginAplicacionIn.
     * 
     * @return
     *     possible object is
     *     {@link LoginAplicacionTypeIn }
     *     
     */
    public LoginAplicacionTypeIn getLoginAplicacionIn() {
        return loginAplicacionIn;
    }

    /**
     * Define el valor de la propiedad loginAplicacionIn.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginAplicacionTypeIn }
     *     
     */
    public void setLoginAplicacionIn(LoginAplicacionTypeIn value) {
        this.loginAplicacionIn = value;
    }

}
