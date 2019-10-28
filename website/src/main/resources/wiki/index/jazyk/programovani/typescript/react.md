## React

!TODO!

### Deklarace komponenty

```include:typescript
react/src/components/VariousComponentTypes.tsx
```

### Hooks

#### Stav (useState)

```typescript jsx
import React, { useState, useEffect } from 'react';

function Example() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );
}
```

#### Životní cyklus metody (useEffect)

```typescript jsx
import React, { useState, useEffect } from 'react';

function Example() {
  const [count, setCount] = useState(0);

  // Similar to componentDidMount and componentDidUpdate:
  useEffect(() => {
    // Update the document title using the browser API
    document.title = `You clicked ${count} times`;
  });

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );
}
```

#### Kontext (useContext)

```typescript jsx
import React from 'react';

const UserContext = React.createContext({})

// Provider - The component that provides the value
export const UserProvider = UserContext.Provider
// Consumer - A component that is consuming the value
export const UserConsumer = UserContext.Consumer
export default UserContext
```
