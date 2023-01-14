export const isNotEmpty = (text: string | null): boolean => {
    return text != null && text.trim() != "";
}

export const convertSalutation = (text: string | null): string | null => {
    const arr = [
        {id: 'male', value: 'Male'},
        {id: 'female', value: 'Female'}
    ];

    if (text != null) {
        const foundObject = arr.find(value => value.id === text);

        if (foundObject) {
            return foundObject.value;
        }
    }

    return null;
}

export function generateId() {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (c: string) => {
        const r = Math.random() * 16 | 0;
        // eslint-disable-next-line no-mixed-operators
        const v = c === "x" ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}
