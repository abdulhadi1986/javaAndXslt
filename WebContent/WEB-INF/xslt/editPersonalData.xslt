<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns="http://www.w3.org/1999/xhtml">
  <xsl:output method="xml" indent="yes" encoding="UTF-8"
     doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
     doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
  <!--

  ***************************************************************
  ** Top level template. Creates the framework for the XHTML page
  ************************************************************-->
  <xsl:template match="/">
    <html>
      <head><title>Edit Personal Information</title></head>
      <body>
        <xsl:apply-templates select="page/personalData"/>
      </body>
    </html>
  </xsl:template>
  <!--

  ***************************************************************
  ** Match the <personalData> element.
  ************************************************************-->
  <xsl:template match="personalData">
    <h3>Personal Information</h3>
    <xsl:if test="../requiredFieldsMissing">
      <div style="color: red; font-size: larger;">
        Error: one or more required fields are missing.
      </div>
    </xsl:if>
    <i>Fields marked with (*) are required.</i>
    <form action="/chap6/personalData" method="post">
      <table border="0" cellpadding="2" cellspacing="0">
        <!-- Select all immediate children, such as firstName,
             lastName, daytimePhone, etc... -->
        <xsl:apply-templates/>
      </table>
      <div align="center">
        <hr/>
        <input type="submit" name="submitBtn" value="Submit"/>
      </div>
    </form>
  </xsl:template>
  <!--

  ***************************************************************
  ** Output a new row in the table for each field.
  ************************************************************-->
  <xsl:template 
      match="firstName|lastName|daytimePhone|eveningPhone|email">
    <tr>
      <xsl:if test="@required='true'
                    and ../../requiredFieldsMissing
                    and .=''">
        <xsl:attribute name="style">
          <xsl:text>color:red; font-weight:bold;</xsl:text>
        </xsl:attribute>
      </xsl:if>
      <td>
        <xsl:choose>
          <xsl:when test="name( )='firstName'">
            First Name:</xsl:when>
          <xsl:when test="name( )='lastName'">
            Last Name:</xsl:when>
          <xsl:when test="name( )='daytimePhone'">
            Daytime Phone:</xsl:when>
          <xsl:when test="name( )='eveningPhone'">
            Evening Phone:</xsl:when>
          <xsl:when test="name( )='email'">
            Email:</xsl:when>
        </xsl:choose>
      </td>
      <td>
        <input type="text" name="{name( )}" value="{.}"/>
      </td>
      <td>
        <xsl:if test="@required='true'">*</xsl:if>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>