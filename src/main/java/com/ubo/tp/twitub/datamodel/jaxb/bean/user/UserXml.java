//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.22 at 01:32:54 PM CET 
//

package com.ubo.tp.twitub.datamodel.jaxb.bean.user;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for UserXml complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="UserXml">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserTag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AvatarPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence>
 *           &lt;element name="follows" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserXml", propOrder = {"id", "userTag", "userPassword", "name", "avatarPath", "follows"})
@XmlRootElement
public class UserXml {

    @XmlElement(name = "ID", required = true)
    protected String id;
    @XmlElement(name = "UserTag", required = true)
    protected String userTag;
    @XmlElement(name = "UserPassword", required = true)
    protected String userPassword;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "AvatarPath", required = true)
    protected String avatarPath;
    @XmlElement(required = true)
    protected List<String> follows;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link String }
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the userTag property.
     *
     * @return possible object is {@link String }
     */
    public String getUserTag() {
        return userTag;
    }

    /**
     * Sets the value of the userTag property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUserTag(String value) {
        this.userTag = value;
    }

    /**
     * Gets the value of the userPassword property.
     *
     * @return possible object is {@link String }
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the value of the userPassword property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUserPassword(String value) {
        this.userPassword = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the avatarPath property.
     *
     * @return possible object is {@link String }
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Sets the value of the avatarPath property.
     *
     * @param value allowed object is {@link String }
     */
    public void setAvatarPath(String value) {
        this.avatarPath = value;
    }

    /**
     * Gets the value of the follows property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the follows property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     *
     * <pre>
     * getFollows().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     */
    public List<String> getFollows() {
        if (follows == null) {
            follows = new ArrayList<String>();
        }
        return this.follows;
    }

}
