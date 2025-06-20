
# AI-Politics-Sim

Politik - Fight The AI



## Dependencies

#### Get API KEY

```http
  https://api.together.ai/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. to connect to LLM |

#### Get JSON Library 

```http
  https://mvnrepository.com/artifact/org.json/json
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `json`      | `library` | **Required**. to parse the LLM In- & Output  |



## Getting Started
 Getting Started
Clone the repo, install the dependencies, and you’re ready to go.

🔑 API Key Setup
To connect with the LLM backend (e.g., Together AI), you'll need an API key.

Options:
1. Ask Finn Behrens for an API KEY
2. Create your own API KEY

#### Create your Own API KEY
Visit 👉 https://api.together.ai/

Sign up or log in.

Copy your API key.
Paste your API key inside the file called API_KEY.txt in your /src directory.

📄 File Structure:

```http
  src/
├── Game/
│   └── ...
├── Player/
│   └── ...
└── API_KEY.txt  ← Place your key here
```

📦 Dependencies:
🧩 JSON library:
Used for parsing LLM responses.

Download: https://mvnrepository.com/artifact/org.json/json

Parameter Type Description
json library: Required to parse the LLM input and output.

🌐 Together.ai API
Backend LLM services powered by Together.ai.

🔗 Get your key here: https://api.together.ai/.

Parameter: Type: Description:
api_key: string. Required for LLM communication.



## About LLM Models, Tokens and Pricing
I used the following LLM models:
Free model: "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free"
Preferred model: "deepseek-ai/DeepSeek-V3".

The preferred model is: Deepseek-V3 costs 1.25 USD per 1M tokens.
I have put 1 USD into the account, which should be enough for multiple play sessions.
For context, you can check how many tokens you have via https://lunary.ai/deepseek-tokenizer.

Please remember:
Every LLM call has:
1. Your new input
2. Prompt
3. Entire log

Tokens can add up rather quickly.
Please use them wisely. If you run out of money, please use the free model.
The results will vary, but will still be acceptable.