import {convertSalutation, isNotEmpty} from "./Utils";

test('test convertSalutation', () => {
    const x1 = convertSalutation('male');
    const x2 = convertSalutation('female');
    const x3 = convertSalutation('');
    const x4 = convertSalutation('Frau');
    const x5 = convertSalutation(null);

    expect(x1).toBe("Male");
    expect(x2).toBe("Female");
    expect(x3).toBe(null);
    expect(x4).toBe(null);
    expect(x5).toBe(null);
});

test('test isNotEmpty', () => {
    const x1 = isNotEmpty('male');
    const x2 = isNotEmpty('    female    ');
    const x3 = isNotEmpty('');
    const x4 = isNotEmpty('  ');
    const x5 = isNotEmpty(null);

    expect(x1).toBe(true);
    expect(x2).toBe(true);
    expect(x3).toBe(false);
    expect(x4).toBe(false);
    expect(x5).toBe(false);
});
