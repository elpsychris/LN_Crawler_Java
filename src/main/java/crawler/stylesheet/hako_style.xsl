<xsl:stylesheet version="1.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://t3.com/2018/xml"
                xmlns="http://t3.com/2018/project-page" xmlns:xsd="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="t:project-page">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="@host"/>
        <project-page>
            <next-page>
                <xsl:value-of
                        select="$listDoc//div[@class='pagination_wrap']/a[contains(@class,'current')]/following-sibling::a[1][text()!='Cuối']/@href"/>
            </next-page>
            <projects>
                <xsl:for-each select="$listDoc//article[@class='listext-item clear']/div[1]/a">
                    <xsl:variable name="cur_project_link" select="@href"/>
                    <xsl:variable name="projDoc" select="document($cur_project_link)"/>
                    <xsl:if test="boolean($projDoc//h2[@class='listall_title animation fade-in-up']/a)">
                        <project>
                            <id>
                                <xsl:value-of
                                        select="normalize-space($projDoc//h2[@class='listall_title animation fade-in-up']/a/@href)"/>
                            </id>
                            <name>
                                <xsl:value-of
                                        select="normalize-space($projDoc//h2[@class='listall_title animation fade-in-up']/a)"/>
                            </name>
                            <alter-name>
                                <xsl:variable name="alterName"/>
                                <xsl:for-each
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Tên khác']/../span[@class='ln_info-value col-7']/span">
                                    <xsl:value-of select="concat(.,', ')"/>
                                </xsl:for-each>
                            </alter-name>
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
                                        <genre-id><xsl:value-of select="."/></genre-id>
                                    </genre>
                                </xsl:for-each>
                            </genres>
                            <view>
                                <xsl:value-of
                                        select="$projDoc//div[@class='ln_info-item clear']/span[text()='Lượt xem']/../span[@class='ln_info-value col-7']"/>
                            </view>
                            <link>
                                <xsd:value-of select="$cur_project_link"/>
                            </link>
                            <xsl:variable name="group"
                                          select="$projDoc//section[@class='fantrans-section']/div[contains(@class,'value')]/a"/>
                            <xsl:variable name="group-link"
                                          select="$projDoc//section[@class='fantrans-section']/div[contains(@class,'value')]/a/@href"/>
                            <xsl:variable name="uploader" select="$projDoc//span[@class='listall-user_name']/a"/>
                            <xsl:variable name="uploader-link" select="$projDoc//span[@class='listall-user_name']/a/@href"/>
                            <updates>
                                <xsl:for-each
                                        select="$projDoc//section[@class='ln_chapters-volume basic-section mobile-view clear']">
                                    <xsl:variable name="volume-name"
                                                  select="normalize-space(.//span[@class='sect-title']/a/text())"/>
                                    <xsl:variable name="volume-img"
                                                  select="normalize-space(.//div[@class='ln_chapters-vol_img col-3 col-3-m col-2-l col-2-xl']/a/img/@src)"/>
                                    <xsl:for-each select=".//div[contains(@class,'ln_chapters-vol_main')]/article">
                                        <update>
                                            <volume-name>
                                                <xsl:value-of select="$volume-name"/>
                                            </volume-name>
                                            <volume-cover>
                                                <xsl:value-of
                                                        select="$volume-img"/>
                                            </volume-cover>
                                            <chapter-name>
                                                <xsl:value-of select="normalize-space(./div[1]/a/.)"/>
                                            </chapter-name>
                                            <chapter-date>
                                                <xsl:value-of select="normalize-space(./div[2]/.)"/>
                                            </chapter-date>
                                            <update-group>
                                                <group-link>
                                                    <xsl:if test="$group">
                                                        <xsl:value-of select="$group-link"/>
                                                    </xsl:if>
                                                    <xsl:if test="not(boolean($group))">
                                                        <xsl:value-of select="$uploader-link"/>
                                                    </xsl:if>
                                                </group-link>
                                                <group-name>
                                                    <xsl:if test="$group">
                                                        <xsl:value-of select="$group"/>
                                                    </xsl:if>
                                                    <xsl:if test="not(boolean($group))">
                                                        <xsl:value-of select="$uploader"/>
                                                    </xsl:if>
                                                </group-name>
                                            </update-group>
                                            <update-link>
                                                <xsl:value-of select="normalize-space(./div[1]/a/@href)"/>
                                            </update-link>
                                        </update>
                                    </xsl:for-each>
                                </xsl:for-each>
                            </updates>
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