package com.vodafone.warehousemaangement.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperUtils {

	@Autowired
	private ModelMapper modelMapper;

	public ObjectMapperUtils(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param <D>      type of result object.
	 * @param <T>      type of source object to map from.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object.
	 * @return new object of <code>outClass</code> type.
	 */
	public <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param entityList list of entities that needs to be mapped
	 * @param outCLass   class of result list element
	 * @param <D>        type of objects in result list
	 * @param <S>        type of entity in <code>entityList</code>
	 * @return list of mapped object with <code><D></code> type.
	 */
	public <D, S> List<D> mapAll(final Collection<S> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}

//	public <S, T extends DTO> List<T> mapList(List<S> source, Class<T> target) {
//		modelMapper = getMapper(target);
//		return source.stream().map(el -> modelMapper.map(el, target)).collect(Collectors.toList());
//	}
//
//	public <T extends DTO> ModelMapper getMapper(Class<T> target) {
//
//		modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE).setFieldMatchingEnabled(true)
//				.setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));
//		return updateMapping(modelMapper, target);
//	}
//
//	public <T extends DTO> ModelMapper updateMapping(ModelMapper mapper, Class<T> dto) {
//		try {
//			Constructor<T> constructor = dto.getConstructor();
//			T instance = constructor.newInstance();
//			return instance.updateModelMapper(mapper, this);
//		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException
//				| InvocationTargetException e) {
//			throw new MappingException(dto.getName());
//		}
//	}

}
