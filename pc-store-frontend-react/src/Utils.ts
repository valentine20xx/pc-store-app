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
