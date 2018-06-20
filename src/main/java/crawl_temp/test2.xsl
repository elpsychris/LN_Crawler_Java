<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <project>
            <xsl:apply-templates/>
        </project>
    </xsl:template>
    <xsl:template name="project-name" match="h2[@class='listall_title animation fade-in-up']/a">
        <name>
            <xsl:value-of select="normalize-space(.)"/>
        </name>
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template name="synopsis" match="div[@class = 'listall_summary none force-block-l']">
        <synopsis>
            <xsl:for-each select="p">
                <xsl:value-of select="normalize-space(.)"/>
            </xsl:for-each>
        </synopsis>
    </xsl:template>
    <xsl:template name="alter-name" match="div[@class='ln_info-item clear'][8]/span[@class='ln_info-value col-7']">
        <alter-names>
            <xsl:for-each select="span">
                <alter-name>
                    <xsl:value-of select="normalize-space(.)"/>
                </alter-name>
            </xsl:for-each>
        </alter-names>
    </xsl:template>

    <xsl:template name="author" match="div[@class='ln_info-item clear'][2]//a">
        <author>
            <xsl:value-of select="."/>
        </author>
    </xsl:template>
    <xsl:template name="tags" match="div[@class='ln_info-item clear'][4]/span[@class='ln_info-value col-7']">
        <tags>
            <xsl:for-each select="a">
                <tag>
                    <xsl:value-of select="."/>
                </tag>
            </xsl:for-each>
        </tags>
    </xsl:template>
    <xsl:template name="view" match="div[@class='ln_info-item clear'][5]/span[@class='ln_info-value col-7']">
        <view>
            <xsl:value-of select="."/>
        </view>
    </xsl:template>

    <xsl:template name="volume" match="//div[@class='reg-part col-12 col-2-3-l']">
        <volumes>
            <xsl:for-each select="section[@class='ln_chapters-volume basic-section mobile-view clear']">
                <volume>
                    <volume-name>
                        <xsl:value-of select="normalize-space(//span[@class='sect-title']/a/text())"/>
                    </volume-name>
                    <volume-cover>
                        <xsl:value-of
                                select="normalize-space(//div[@class='ln_chapters-vol_img col-3 col-3-m col-2-l col-2-xl']/a/img/@src)"/>
                    </volume-cover>
                    <chapters>
                        <xsl:for-each
                                select="//div[@class='ln_chapters-vol_main listext-table col-9 col-9-m col-10-l col-10-xl']/article">
                            <chapter>
                                <chapter-name>
                                    <xsl:value-of select="normalize-space(div[1]/a/.)"/>
                                </chapter-name>
                                <chapter-date>
                                    <xsl:value-of select="normalize-space(div[2]/.)"/>
                                </chapter-date>
                            </chapter>
                        </xsl:for-each>
                    </chapters>
                </volume>
            </xsl:for-each>
        </volumes>
    </xsl:template>


    <xsl:template name="no-text" match="text()|@*">
    </xsl:template>

</xsl:stylesheet>