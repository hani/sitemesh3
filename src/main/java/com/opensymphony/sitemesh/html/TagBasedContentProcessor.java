package com.opensymphony.sitemesh.html;

import com.opensymphony.sitemesh.Content;
import com.opensymphony.sitemesh.ContentProcessor;
import com.opensymphony.sitemesh.Context;
import com.opensymphony.sitemesh.InMemoryContent;
import com.opensymphony.sitemesh.html.rules.PageBuilder;
import com.opensymphony.sitemesh.tagprocessor.State;
import com.opensymphony.sitemesh.tagprocessor.TagProcessor;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * {@see ContentProcessor} implementation that is build on {@link TagProcessor}.
 *
 * @see HtmlContentProcessor for an implementation example.
 *
 * @author Joe Walnes
 */
public abstract class TagBasedContentProcessor<C extends Context> implements ContentProcessor<C> {

    @Override
    public Content build(CharBuffer data, C context) throws IOException {
        InMemoryContent content = new InMemoryContent(data);
        PageBuilder builder = new InMemoryContentBuilder(content);

        TagProcessor processor = new TagProcessor(data);

        // Additional rules - designed to be tweaked.
        setupRules(processor.defaultState(), builder, context);

        // Run the processor.
        processor.process();

        postProcess(content, builder, processor);
        return content;
    }

    /**
     * Override this to add custom rules.
     */
    protected void setupRules(State defaultState, PageBuilder builder, C context) {
        // No op.
    }

    /**
     * Override this to perform any additional processing after the tag processor has completed.
     */
    protected void postProcess(Content content, PageBuilder pageBuilder, TagProcessor processor) {
        // No op.
    }

}
