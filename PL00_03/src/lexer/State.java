package lexer;

import static lexer.TokenType.ID;
import static lexer.TokenType.INT;
import static lexer.TransitionOutput.GOTO_ACCEPT_ID;
import static lexer.TransitionOutput.GOTO_ACCEPT_INT;
import static lexer.TransitionOutput.GOTO_EOS;
import static lexer.TransitionOutput.GOTO_FAILED;
import static lexer.TransitionOutput.GOTO_MATCHED;
import static lexer.TransitionOutput.GOTO_SIGN;
import static lexer.TransitionOutput.GOTO_START;
import static lexer.TransitionOutput.GOTO_SHARP; //#T�� #F�� ��Ÿ���� ���� ���¸� �߰��Ѵ�.

enum State {
	START { // ��ȯ ���ۺκ�
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // ����char�� ����
			char v = ch.value();
			switch (ch.type()) {
			case LETTER: // ���� char�� �����̸� accept_id�� ������
				context.append(v);
				return GOTO_ACCEPT_ID;
			case DIGIT: // ���� char�� �����̸� accept_digit�� ������
				context.append(v);
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // (, ), *, /, >, <, =, \, +, - �̸� Sign���� ������.
				context.append(v);
				if (v == '#') { // �׷��� #�� ���Ŀ� T�� F�� ���;� �ϹǷ� SHARP�� ���� �� �����
					return GOTO_SHARP;
				}
				context.getCharStream().pushBack(v); // pushBack�� ����Ͽ� �ڷ� ��ĭ
														// �̵��Ѵ�. �ڷ� ��ĭ �̵��ϴ� ������
														// #T�� #F�� ���Ⱑ ����
														// �ٷ� �̾����Ƿ� pushBack����
														// �ڷ� ��ĭ �̷���ش�.
				return GOTO_SIGN;
			case WS: // ���Ⱑ ������ �ٽ� START�� ����.
				return GOTO_START;
			case END_OF_STREAM: // STREAM�� ������ EOS�� ����.
				return GOTO_EOS;
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_ID { // Letter�� �ν����� ID�� ����
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // ����char�� ����
			char v = ch.value();
			switch (ch.type()) {
			case LETTER:
			case DIGIT:
				context.append(v);
				return GOTO_ACCEPT_ID; // letter�� �ν����� �ٽ� ���ڳ� ���ڸ� ������ id�� �ν��Ѵ�.
			case SPECIAL_CHAR: // Ư�� ���ڷ� �ν�
				if (v == '?') { // �νĵ� char�� ?�̸�
					context.append(v);
					return GOTO_ACCEPT_ID; // �ٽ� ID�� �����Ѵ�.
				}
				return GOTO_FAILED; // �ƴϸ� FAILED�� ����.
			case WS: // ���⳪ ������ ofName�Լ��� �̿��Ͽ� keyword�� �ν��ϰ� �Ѵ�.
			case END_OF_STREAM:
				return GOTO_MATCHED(Token.ofName(context.getLexime()));
			default:
				throw new AssertionError();
			}
		}
	},
	ACCEPT_INT { // DIGIT�� �ν����� INT�� ����
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // ���� char�� ����
			switch (ch.type()) {
			case LETTER: // ���ڰ� ������ failed�� ����.
				return GOTO_FAILED;
			case DIGIT: // ���ڰ� ������ int�� ����.
				context.append(ch.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // Ư�����ڰ� ������ failed�� ����.
				return GOTO_FAILED;
			case WS: // ���⳪ ������ int�� match���ְ� ������ �־��ش�.
			case END_OF_STREAM:
				return GOTO_MATCHED(INT, context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SIGN { // Ư�����ڷ� �ν����� SIGN���� ����
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar(); // ���� char�� �а� �� ����
															// char�� �д´�. ������ Ư�����ڰ� ������ �ٷ� ��� ���Ⱑ ������ �����̴�.
			Char ch_next = context.getCharStream().nextChar();
			char v = ch.value();
			switch (ch_next.type()) { // ch_next�� Ÿ���� �������� ����Ѵ�.
			case LETTER: // ���ڰ� ������ failed�� ����.
				return GOTO_FAILED;
			case DIGIT: // ���ڰ� ������ digit�� ����.
				context.append(ch_next.value());
				return GOTO_ACCEPT_INT;
			case SPECIAL_CHAR: // Ư�����ڰ� ������ failed�� ����.
				return GOTO_FAILED;
			case WS: // ���Ⱑ �����ų� ������ TokenType�� fromSpecialCharactor�Լ����� ������
						// �޾��� ���ڸ� match��Ų��.
			case END_OF_STREAM:
				return GOTO_MATCHED(TokenType.fromSpecialCharactor(v), context.getLexime());
			default:
				throw new AssertionError();
			}
		}
	},
	SHARP { // Ư�����ڰ� SHARP�� ���
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch (ch.type()) {
			case LETTER: // #�� ���´���
				if (v == 'T') { // T�� ������ TokenType�� TRUE�� match��Ų��.
					context.append(v);
					return GOTO_MATCHED(TokenType.TRUE, context.getLexime());
				} else if (v == 'F') { // F�� ������ TokenType�� FALSE�� match��Ų��.
					context.append(v);
					return GOTO_MATCHED(TokenType.FALSE, context.getLexime());
				}
				return GOTO_FAILED; // T�� F�� �ƴϸ� failed�� ��Ÿ����.
			case DIGIT: // #������ ����, Ư������, ���Ⱑ �����ų� ������ failed�� ����.
			case SPECIAL_CHAR:
			case WS:
			case END_OF_STREAM:
				return GOTO_FAILED;
			default:
				throw new AssertionError();
			}
		}
	},
	MATCHED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	};

	abstract TransitionOutput transit(ScanContext context);
}
