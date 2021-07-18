<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Orangery</h2>
                <table border="1">
                    <tr>
                        <th rowspan="2">Name</th>
                        <th rowspan="2">Soil</th>
                        <th rowspan="2">Origin</th>
                        <th colspan="3">Visual parameters</th>
                        <th colspan="4">Growing tips</th>
                    </tr>
                    <tr>
                        <th>Stem color</th>
                        <th>Leaf color</th>
                        <th>Average size</th>
                        <th>Temperature</th>
                        <th>Light loving</th>
                        <th>Watering</th>
                        <th>Multiplying</th>
                    </tr>
                    <xsl:for-each select="orangery/flower">
                        <xsl:sort select="growingTips/temperature"/>
                        <tr>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="soil"/>
                            </td>
                            <td>
                                <xsl:value-of select="origin"/>
                            </td>
                            <td>
                                <xsl:value-of select="visualParameters/stemColor"/>
                            </td>
                            <td>
                                <xsl:value-of select="visualParameters/leafColor"/>
                            </td>
                            <td>
                                <xsl:value-of select="visualParameters/averageSize"/>
                            </td>
                            <td>
                                <xsl:value-of select="growingTips/temperature"/>
                            </td>
                            <td>
                                <xsl:value-of select="growingTips/light-loving"/>
                            </td>
                            <td>
                                <xsl:value-of select="growingTips/watering"/>
                            </td>
                            <td>
                                <xsl:value-of select="growingTips/multiplying"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

