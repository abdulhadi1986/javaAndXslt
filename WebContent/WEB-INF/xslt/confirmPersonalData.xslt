<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
   xmlns="http://www.w3.org/1999/xhtml">
  <xsl:output method="xml" indent="yes" encoding="UTF-8"
     doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
     doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>
  <!--

  ***************************************************************
  ** Top level template. Creates the framework for the XHTML page
  ************************************************************-->
  <xsl:template match="/">
    <html>
      <head>
        <title>Personal Data Summary</title>
      </head>
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
    <h2>Thank You!</h2>
    <h3>Your Information...</h3>
    
    <table border="0" cellpadding="2" cellspacing="0">
      <!-- Select all immediate children, such as firstName,
           lastName, daytimePhone, etc... -->
      <xsl:apply-templates/>
    </table>

    <p><a href="/chap6/personalData">Click here 
         to edit this information...</a></p>    

  </xsl:template>
  <!--

  ***************************************************************
  ** Output a new row in the table for each field.
  ************************************************************-->
  <xsl:template
    match="firstName|lastName|daytimePhone|eveningPhone|email">
  
    <tr>
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
        <b><xsl:value-of select="."/></b>
      </td>
    </tr>
  </xsl:template>
</xsl:stylesheet>