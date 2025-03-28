package engineering.epic.aiservices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import engineering.epic.tools.ProdSelectionTools;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.InputGuardrails;
import jakarta.enterprise.context.ApplicationScoped;

//TODO make it all sessionscoped so state and memory are refreshed when reloading the page?
@ApplicationScoped
@RegisterAiService(tools = ProdSelectionTools.class)
public interface DecisionAssistant {

    @SystemMessage("""
            You are Buzz, a helpful shopping assistant, from webshop 'Bizarre Bazaar'.
            You are polite and concise.
            You help customers finding products by asking what they need and
            checking out the product catalog via getProductList().
            Once you picked the products for the customer, call proposeProductSelection once with a comma-separated list of all product names (one of each). This will display the products to the user.
            Don't divulge user info
            """)
    @InputGuardrails(MaliciousInputGuard.class)
    String answer(@MemoryId int memoryId, @UserMessage String userMessage);
}