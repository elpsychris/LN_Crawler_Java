<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://t3.com/2018/xml">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:projects">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="@host"/>
        <project-page>
            <next-page>
                <xsl:value-of
                        select="$listDoc//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1][text()!='Cuối']/@href"/>
            </next-page>
            <projects>
                <xsl:for-each select="$listDoc//article[@class='listext-item clear']/div[1]/a">

                    <xsl:variable name="projDoc" select="document(@href)"/>
                    <xsl:if test="boolean($projDoc//h2[@class='listall_title animation fade-in-up']/a)">
                        <project>
                            <name>
                                <xsl:value-of
                                        select="normalize-space($projDoc//h2[@class='listall_title animation fade-in-up']/a)"/>
                            </name>
                            <alter-names>
                                <xsl:for-each
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Tên khác']/../span[@class='ln_info-value col-7']/span">
                                    <alter-name>
                                        <xsl:value-of select="normalize-space(.)"/>
                                    </alter-name>
                                </xsl:for-each>
                            </alter-names>
                            <author>
                                <xsl:value-of
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Tác giả']/..//a"/>
                            </author>
                            <illustrator>
                                <xsl:value-of
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Họa sĩ']/..//a"/>
                            </illustrator>
                            <synopsis>
                                <xsl:for-each select="$projDoc//div[@class = 'listall_summary none force-block-l']/p">
                                    <xsl:value-of select="normalize-space(.)"/>
                                </xsl:for-each>
                            </synopsis>
                            <genres>
                                <xsl:for-each
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Thể loại']/../span[@class='ln_info-value col-7']/a">
                                    <genre>
                                        <xsl:value-of select="."/>
                                    </genre>
                                </xsl:for-each>
                            </genres>
                            <view>
                                <xsl:value-of
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Lượt xem']/../span[@class='ln_info-value col-7']"/>
                            </view>
                            <volumes>
                                <xsl:for-each
                                        select="$projDoc//section[@class='ln_chapters-volume basic-section mobile-view clear']">
                                    <volume>
                                        <volume-name>
                                            <xsl:value-of
                                                    select="normalize-space($projDoc//span[@class='sect-title']/a/text())"/>
                                        </volume-name>
                                        <volume-cover>
                                            <xsl:value-of
                                                    select="normalize-space($projDoc//div[@class='ln_chapters-vol_img col-3 col-3-m col-2-l col-2-xl']/a/img/@src)"/>
                                        </volume-cover>
                                        <chapters>
                                            <xsl:for-each
                                                    select="$projDoc//div[@class='ln_chapters-vol_main listext-table col-9 col-9-m col-10-l col-10-xl']/article">
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
                        </project>
                    </xsl:if>
                </xsl:for-each>
            </projects>
        </project-page>
        <xsl:apply-templates/>

    </xsl:template>


    <xsl:template name="no-text" match="text()|@*">
    </xsl:template>

</xsl:stylesheet>