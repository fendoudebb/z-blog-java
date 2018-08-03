package com.msj.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.*;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * zbj: created on 2018/7/1 22:15.
 */
public class MarkdownUtil {
    private static final List<Extension> extensions = Arrays.asList(TablesExtension.create(), AutolinkExtension.create(), StrikethroughExtension.create());
    private static final Parser parser = Parser.builder()
            .extensions(extensions)
            .build();
    private static final HtmlRenderer renderer = HtmlRenderer.builder()
            .extensions(extensions)
            .attributeProviderFactory(context -> new HtmlAttributeProvider())
            .softbreak("<br/>")
            // 修飾代碼塊內容
            .nodeRendererFactory(new LineNumberNodeRendererFactory())
            .build();

    public static String parse(String content) {
        Node document = parser.parse(content);
        return renderer.render(document);
    }

    static class HtmlAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof TableBlock) { //判斷是否是Table表格，是的話，加上css類樣式 table table-bordered
                attributes.put("class", "table table-bordered table-striped");
            } else if (node instanceof Link) {
                attributes.put("target", "_blank");
            } else if (node instanceof Image) {
                attributes.put("style", "display:block;margin:0 auto;max-width:100%");
                String alt = attributes.get("alt");
                attributes.put("title", alt);
            }
        }
    }

    static class LineNumberNodeRendererFactory implements HtmlNodeRendererFactory {

        @Override
        public NodeRenderer create(HtmlNodeRendererContext htmlNodeRendererContext) {
            return new NodeRenderer() {
                @Override
                public Set<Class<? extends Node>> getNodeTypes() {
                    return Collections.singleton(FencedCodeBlock.class);
                }

                @Override
                public void render(Node node) {
                    HtmlWriter html = htmlNodeRendererContext.getWriter();
                    FencedCodeBlock codeBlock = (FencedCodeBlock) node;
                    Map<String,String> attrs = new HashMap<>();
                    if (!StringUtils.isEmpty(codeBlock.getInfo())) {
                        attrs.put("class","language-" + codeBlock.getInfo());
                    }
                    html.line();
                    html.tag("pre");
                    html.tag("code",attrs);
                    html.tag("ol");
                    String data = codeBlock.getLiteral();
                    String[] split = data.split("\n");
                    for (String s : split) {
                        html.tag("li");
                        html.text(s + "\n");
                        html.tag("/li");
                    }
                    html.tag("/ol");
                    html.tag("/code");
                    html.tag("/pre");
                    html.line();
                }
            };
        }
    }

}
