<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="main[@class='sect-body listext-table widthfluid clear']">
        <xsl:element name="projects">
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
    <xsl:template match="article[@class='listext-item clear']">
        <project><xsl:apply-templates/></project>
    </xsl:template>
    <xsl:template match="//div[@class='listext_attr listext_title col-12 col-2-3-m col-6-l']/a">
        <name><xsl:value-of select="."/></name>
        <link><xsl:value-of select="./@href"/></link>
    </xsl:template>
    <xsl:template match="text()|@*">
    </xsl:template>

</xsl:stylesheet>