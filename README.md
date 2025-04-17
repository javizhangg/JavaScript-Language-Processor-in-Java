# 🧠 JavaScript Language Processor in Java

## 📘 Overview
This project is a **Java‑based compiler front‑end** for a **JavaScript‑like teaching language**. It reads a source file (`prueba.txt`) and performs — completely from scratch — the classic compiler pipeline:

| Stage | What we built | Key classes |
|-------|---------------|-------------|
| **Lexical analysis** | DFA‑driven scanner that produces `<code, attribute>` tokens and detects lexical errors via `Error.java`. | `AnalizadorLexico`, `Matriz`, `Token` |
| **Syntax analysis**  | Hand‑written **LL(1)** parser generated from our `gramatica.txt`, using **FIRST / FOLLOW** sets (`First.java`) and a predictive table. | `AnalizadorSintactico` |
| **Semantic checks + Symbol tables** | Multiple symbol‑table levels (`TablasDeSimbolos`, `TS`, `Simbolo`) and contextual rules for types, scopes, parameter checking, return types, etc. | `AnalizadorSemantico` |
| **Error reporting** | Unified lexical, syntactic and semantic messages with codes **100–313** (`Error.java`). | `Error` |
| **Artefact writers** | Separate files with the token stream, symbol tables and parse trace. | `AnalizadorLexico`, `AnalizadorSintactico` |

Everything is pure Java — **no generator tools (JFlex, CUP, ANTLR, etc.)** — to maximise learning of the internal algorithms.

---

## 🔍 Feature Highlights

### ✳️ Lexical Analysis
* One‑pass DFA (`Matriz.java`) loaded from a CSV‑like transition table.
* Supports identifiers, keywords, string/number literals, operators, comments and UTF‑8.
* Hard limits & special checks (e.g. max‑length strings, integer range) trigger specific error codes (100‑108).

### 🌲 Predictive Parsing
* Grammar in `gramatica.txt` ⟶ FIRST/FOLLOW sets in `First.java` ⟶ table‑driven descent in `AnalizadorSintactico.java` (≈150 productions, 49 numbered actions in the parse trace).
* Full semantic actions inter‑leaved with parsing (type propagation, parameter matching, memory offsets, etc.).

### 📘 Symbol‑Table Management
* Global + local scope tables with automatic destruction on block exit (`TablasDeSimbolos.LiberaTablaTS`).
* Each `Simbolo` keeps lexeme, type, return type, parameters, displacement, label, etc.

### 🚨 Unified Error System
Three categories — **lexical (100‑199), syntactic (200‑299), semantic (300‑399)** — printed immediately and also routed to the token / parse files.

---

## 🚨 Detailed Error Code Ranges
We follow a **three‑tier layout** so that the first digit tells you immediately _where_ the problem happened:

| Range | Category | Thrown from | Typical causes |
|-------|----------|-------------|----------------|
| **100 – 199** | **Lexical errors** | `AnalizadorLexico` / `AFD` | Bad characters, unterminated strings, numbers > 32767, etc. |
| **200 – 299** | **Syntactic errors** | `AnalizadorSintactico` | Unexpected tokens, missing `)`, mismatched `return`, … |
| **300 – 399** | **Semantic errors** | `AnalizadorSemantico` & checks inside the parser | Undeclared identifiers, type mismatches, wrong number of parameters, … |

`Error.java` centralises the messages: every `case` in the giant `switch` converts a numeric **code** into a friendly Spanish description so all front‑end phases just throw an `Error(code, line, extraInfo)`.

---

## 🔢 Token Code Map (excerpt)

| Code | Category / Example | Description |
|------|--------------------|-------------|
| 1    | `<1, contador>`    | Identifier |
| 2    | `<2, 42>`          | Integer constant |
| 3    | `<3, "hola">`    | String constant |
| 4    | `<4, +>`           | Arithmetic op (+ − * / %) |
| 5    | `<5, ==>`          | Relational op (== < > ≤ ≥ !=) |
| 6    | `<6, &&>`          | Logical op (&& || !) |
| 7    | `<7, =>`           | Assignment (=) |
| 8    | `<8, +=>`          | Compound assignment |
| 9    | `<9, var>`         | Declaration keyword |
| 10‑13| `<10, int>` …      | Primitive types (int boolean string void) |
| 14‑15| `<14, output>` …   | I/O keywords (output input) |
| 16‑17| `<16, (>` …        | Parentheses |
| 18‑19| `<18, ,>` / `<19, ;>` | Comma / Semicolon |
| 20‑21| `<20, {>` / `<21, }>` | Braces |
| 22‑29| `<22, if>` …       | Reserved keywords |
| 100  | `<100, ->`         | Any lexical error |

A complete list (1–313) is in `Error.java`.

---

## 📂 Example Run

### 📥 Input (`prueba.txt`)
```js
var int contador;
var boolean encontrado;
function boolean contHasta100(int cont){
    if (cont == 100)
        return true;
    return contHasta100(cont + 1);
}

encontrado = contHasta100(contador);
```

### 📤 Selected Output

<details>
<summary>Tokens (first 12)</summary>

```txt
<9, var> <10, int> <1, contador> <19, ;>
<9, var> <11, boolean> <1, encontrado> <19, ;>
<27, function> <11, boolean> <1, contHasta100> <16, (>
```
</details>

<details>
<summary>Global Symbol Table (excerpt)</summary>

```txt
* LEXEMA: 'contador'
  + Tipo: 'int'
  + Despl: 0
----------- ----------
* LEXEMA: 'contHasta100'
  + Tipo: 'función'
  + numParam: 1
          + TipoParam1: 'int'
  + TipoRetorno: 'boolean'
  + EtiqFuncion: 'EtcontHasta10001'
----------- ----------
```
</details>

<details>
<summary>Parse Trace</summary>

File `FicheroParse` starts with:
```txt
Descendente 1 41 35 1 40 20 24 4 7 10 12 …
```
Each number corresponds to an action in `AnalizadorSintactico` (see comments in code).
</details>

### ✅ Compilation Result
*No lexical, syntactic or semantic errors* → `Compilador` prints **“Compilación finalizada correctamente.”**

---

## 🛠️ Build & Run
```bash
# Compile everything
javac pdl/*.java

# Run the compiler (uses prueba.txt in project root)
java pdl.Compilador

# Generated artefacts
pdl/FicheroTokens   # token stream
pdl/FicheroParse    # numbered parse trace
pdl/FicheroDeTS     # printed symbol tables
```

---

## 🧑‍💻 Authors
* **Zhiwei Zhang**  
* **Xiaolei Zhu**

Developed for the **Language Processors** course, **Universidad Politécnica de Madrid**.

